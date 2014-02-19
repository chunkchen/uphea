
# settings
prjName = 'Uphea'
prjId = 'uphea'
prjDescription = 'Uphea - the art of asking questions'
prjVersion = '1.0.0'

# vars
copyright = 'Copyright &#169; 2003-' + time_year + ' Jodd Team'

# ant
project()
project_header()

lib('jodd')
lib('log')
lib('mail')
lib('servlet')
lib('test')
lib('web')
lib('hsqldb')

#---------------------------------------------------------- app

module('app', '6')
module_compile('src', 'jodd, log, mail')
module_build('src')

artifact_jar('uphea-app', 'app.src')

module_task('dist', 'jar.uphea-app')

#---------------------------------------------------------- web

module('web', '6')
module_compile('src', '>app.src, jodd, log, servlet, web')
module_resource('www')
module_build('src')

war_libs = '''
	${uphea-app.jar}
	${uphea-web.jar}
	lib.jodd
	lib.log
	lib.hsqldb
	lib.mail
	lib.war
'''
	
artifact_jar('uphea-web', 'web.src')
artifact_war('uphea', 'web.www-dir', war_libs, 'jar.uphea-web')

module_task('dist', 'war.uphea')

#---------------------------------------------------------- project

project_task('build', '.app, .web')
project_task('dist', 'build, .app, .web')
project_clean()


project_help('''
Module targets
--------------
build:	builds all modules
dist:	creates distribution jars
clean:	cleans out folders


Project targets
---------------
clean:	cleans all outputs
build:	compile all
dist:	builds distribution war
pack:	pack all
''')

out('''

	<available file="${tomcat.zip}" property="tomcat.exist"/>

	<target name="tomcat.download" unless="tomcat.exist">
		<get src="${tomcat.url}" dest="${tomcat.zip}" verbose="true"/>
	</target>

	<target name="pack" depends="tomcat.download, dist" description="  pack">
		<delete dir="${tmp.dir}"/>
		<mkdir dir="${tmp.dir}"/>
		<delete file="${dist}/uphea.zip"/>
		
		<!-- prepare tomcat -->
		<unzip src="${tomcat.zip}" dest="${tmp.dir}"/>
		<move file="${tmp.dir}/${tomcat.name}" tofile="${tmp.dir}/tomcat"/>
		
		<delete dir="${tmp.dir}/tomcat/webapps"/>
		<mkdir dir="${tmp.dir}/tomcat/webapps"/>
		
		<!-- copy files -->
		<copy file="${war.uphea}" todir="${tmp.dir}/tomcat/webapps" />
		<move file="${tmp.dir}/tomcat/webapps/uphea.war" tofile="${tmp.dir}/tomcat/webapps/ROOT.war" />
		<copy file="${etc.dir}/run-uphea.bat" todir="${tmp.dir}/" />
		<copy file="${etc.dir}/run-uphea.sh" todir="${tmp.dir}/" />

		<!-- configure tomcat -->
		<copy todir="${tmp.dir}/tomcat/bin">
			<fileset dir="${etc.dir}/tomcat/bin"/>
		</copy>

		<!-- working folder -->
		<copy todir="${tmp.dir}/img">
			<fileset dir="work/img"/>
		</copy>

		<!-- the end -->
		<zip destfile="${dist.dir}/uphea.zip" basedir="${tmp.dir}"/>
		<delete dir="${tmp.dir}"/>
	</target>
''')

project_footer()