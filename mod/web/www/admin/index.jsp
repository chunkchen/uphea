<html>
<title>uphea | Administration</title>
<head>
<style type="text/css">
ul.panel		{list-style-type:none; margin:0; padding:0;}
ul.panel li		{margin: 10px; padding:0; float:left; width:101px; text-align:center;}
ul.panel li a	{display: block; text-decoration:none; width:100px; height:70px; padding-top: 20px; border: 1px solid #bbb; font-size: 0.9em; font-family:Tahoma, sans-serif; font-weight:bold;}
ul.panel li a	{background:url("/gfx/cp.png") no-repeat 0 0; color: #666; }
ul.panel li a:hover	{background-position: -150px 0; color:#eee};
</style>

<script type="text/javascript">
$(function() {
	$('ul.panel li').mouseover(function(){
		$(this).stop().animate({marginTop:"20px"}, 100);
	}).mouseout(function(){
		$(this).stop().animate({marginTop:"10px"}, 100);
    });
	$('ul.panel a')
	.css( {backgroundPosition: "0 0"} )
	.mouseover(function(){
		$(this).stop().animate({backgroundPosition:"-150px 0"}, 300);
	}).mouseout(function(){
		$(this).stop().animate({backgroundPosition: "-300px 0"}, 200, "linear", function() {$(this).css( {backgroundPosition: "0 0"} );});
	});
});
</script>
</head>
<body>

<div class="cls" id="page">
	<h1>Uphea Administration</h1>
	<ul class="panel" style="height:140px;">
		<li><a href="users.html"><img src="/gfx/users.png" alt="users"/><br/>Users</a></li>
		<li><a href="upload.html"><img src="/gfx/upload.png" alt="upload"/><br/>Upload</a></li>
		<li><a href="preview.html"><img src="/gfx/preview.png" alt="preview"/><br/>Preview</a></li>
		<li><a href="madvocInfo.html"><img src="/gfx/madvoc.png" alt="madvoc"/><br/>Madvoc</a></li>
		<li><a href="emailStatus.html"><img src="/gfx/emailstatus.png" alt="email status"/><br/>Emails</a></li>
	</ul>
</div>

</body>
</html>