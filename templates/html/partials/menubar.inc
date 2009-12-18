<div class="menuBar">
	<ul><li><a href="<?php echo BASE_URL; ?>">Internments</a></li>
	<?php
		if (userIsAllowed('Cemeteries')) {
			echo "<li><a href=\"".BASE_URL."/cemeteries\">Cemeteries</a></li>";
		}
		if (userIsAllowed('Deeds')) {
			echo "<li><a href=\"".BASE_URL."/deeds\">Deeds</a></li>";
		}
		if (userIsAllowed('Users')) {
			echo "
			<li><a href=\"".BASE_URL."/people\">People</a></li>
			<li><a href=\"".BASE_URL."/users\">Users</a></li>
			";
		}
	?>
</div>
