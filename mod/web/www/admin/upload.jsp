<html>
<body>

<div id="page">
<h1>Upload images</h1>

<p style="margin:20px 0;">Upload JPG image for question. Question id is not required, if it doesn't exist it will be extracted from the filename.</p>

<form action="upload.image" enctype="multipart/form-data" method="post" autocomplete="off">

	<div class="frow">
		<label for="id" class="g3">question id:</label><input type="text" name="id" id="id">
	</div>

	<div class="frow">
		<label for="file" class="g3">file:</label><input type="file" name="file" size="50" id="file">
	</div>
	<div class="frow">
		<input type="submit" value="Submit file" class="g3 push3">
	</div>

</form>
</div>

</body>
</html>