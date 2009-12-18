<div id="banner">
	<div id="application_name"><a href="<?php echo BASE_URL; ?>/">New Application</a></div>
	<div id="location_name">
		<a href="<?php echo BASE_URL; ?>">City of Bloomington, Mark Kruzan, Mayor</a>
	</div>
	<div class="loginForm">
		<?php
			if (isset($_SESSION['USER'])) {
				echo "<a href=\"".BASE_URL."/login/logout.php\">Logout</a>";
			}
			else {
				include APPLICATION_HOME.'/blocks/html/loginForm.inc';
			}
		?>
	</div>
</div>
