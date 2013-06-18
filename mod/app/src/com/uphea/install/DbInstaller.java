package com.uphea.install;

import com.uphea.AppCore;
import com.uphea.domain.Answer;
import com.uphea.domain.Question;
import com.uphea.exception.UpheaException;
import jodd.datetime.JDateTime;
import jodd.db.DbQuery;
import jodd.db.DbSession;
import jodd.db.oom.DbOomQuery;
import jodd.db.oom.sqlgen.DbEntitySql;
import jodd.db.oom.sqlgen.DbSqlBuilder;
import jodd.io.FileUtil;
import jodd.io.StreamUtil;
import jodd.petite.meta.PetiteBean;
import jodd.util.ClassLoaderUtil;
import jodd.util.MathUtil;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * DB installer.
 */
@PetiteBean
public class DbInstaller {

	private static final Logger log = LoggerFactory.getLogger(DbInstaller.class);

	/**
	 * Returns <code>true</code> if database exist. This method does not use
	 * thread provided db session, but common db session.
	 */
	public boolean checkDb() {
		log.debug("Check database.");
		DbSession dbSession = AppCore.ref.createDbSession();
		DbQuery query = new DbQuery(dbSession, "select count(1) from up_user_level");
		try {
			query.execute();
			log.debug("Database OK.");
		    return true;
		} catch (Exception ignored) {
			return false;
		} finally {
			query.close();
			dbSession.closeSession();
		}
	}

	/**
	 * Installs database from the script.
	 */
	public void installDb() {
		log.info("Creating database...");

		executeScriptQueries("/res/db-hsqldb.sql");
		executeScriptQueries("/res/db-hsqldb-countries.sql");

		insertQuestions();
		insertAnswers();

		log.info("Database created.");
	}

	/**
	 * Executes all queries from provided sql file.
	 */
	private void executeScriptQueries(String fileName) {
		log.debug("Executing " + fileName);
		String sql;
		InputStream in = null;
		try {
			in = ClassLoaderUtil.getResourceAsStream(fileName);
			if (in == null) {
				throw new UpheaException(fileName + " not found.");
			}
			sql = FileUtil.readUTFString(in);
		} catch (IOException ioex) {
			throw new UpheaException(ioex);
		} finally {
			StreamUtil.close(in);
		}

		String[] queries = StringUtil.splitc(sql, ";");

		DbSession dbSession = AppCore.ref.createDbSession();
		for (int i = 0; i < queries.length; i++) {
			String q = queries[i];
			q = cleanSql(q);
			if (q.isEmpty()) {
				continue;
			}
			DbQuery query = new DbQuery(dbSession, q);
			query.executeUpdate();
			log.debug("executed query #" + (i + 1));
		}
		dbSession.closeSession();
	}

	/**
	 * Inserts questions and deal with time.
	 */
	private void insertQuestions() {
		String fileName = "/res/db-hsqldb-q.sql";
		log.debug("Executing " + fileName);
		String sql;
		InputStream in = null;
		try {
			in = ClassLoaderUtil.getResourceAsStream(fileName);
			if (in == null) {
				throw new UpheaException(fileName + " not found.");
			}
			sql = FileUtil.readUTFString(in);
		} catch (IOException ioex) {
			throw new UpheaException(ioex);
		} finally {
			StreamUtil.close(in);
		}


		String[] queries = StringUtil.splitc(sql, ";");
		JDateTime jdt = new JDateTime();
		jdt.subDay(61);	// we have 62 pre-defined questions in total

		DbSession dbSession = AppCore.ref.createDbSession();
		for (int i = 0; i < queries.length; i++) {
			String q = queries[i];
			q = cleanSql(q);
			if (q.isEmpty()) {
				continue;
			}
			
			if (q.contains("$date")) {
				q = StringUtil.replace(q, "$date", jdt.toString("YYYYMMDD"));
				jdt.addDay(1);
			}

			DbQuery query = new DbQuery(dbSession, q);
			query.executeUpdate();
			log.debug("executed query #" + (i + 1));
		}

		dbSession.closeSession();
	}


	private void insertAnswers() {
		DbSession dbSession = AppCore.ref.createDbSession();

		DbOomQuery dbQuery = new DbOomQuery(dbSession, DbSqlBuilder.sql("select $C{q.*} from $T{Question q} order by $q.id"));
		List<Question> questions =  dbQuery.list();
		dbQuery.close();
		
		for(Question q : questions) {

			dbQuery = new DbOomQuery(dbSession, DbSqlBuilder.sql("select $C{a.*} from $T{Answer a} where $a.questionId=:questionId"));
			dbQuery.setLong("questionId", q.getId());
			List<Answer> answers =  dbQuery.list();
			dbQuery.close();
			
			for (Answer a : answers) {
				dbQuery = new DbOomQuery(dbSession, DbEntitySql.updateColumn(a, "votes", Integer.valueOf(MathUtil.randomInt(10, 50))));
				dbQuery.executeUpdateAndClose();
			}
		}
	}


	/**
	 * Cleans single sql query.
	 */
	private String cleanSql(String sql) {
		String[] lines = StringUtil.splitc(sql, "\n");
		StringBuilder sb = new StringBuilder(sql.length());
		
		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			if (line.startsWith("--")) {
				continue;
			}
			sb.append(line);
			sb.append(' ');
		}

		return sb.toString();
	}

}