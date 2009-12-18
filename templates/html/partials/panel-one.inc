<div id="panel-one">
	<ul><li><a href="<?php echo BASE_URL; ?>/cemeteries">Cemeteries</a></li>
		<li><a href="<?php echo BASE_URL; ?>/deeds">Deeds</a></li>
		<li><a href="<?php echo BASE_URL; ?>/internments">Internments</a></li>
	</ul>
	<?php
		if (userIsAllowed('Users')) {
			include APPLICATION_HOME.'/templates/html/partials/panel-widgets/Users.inc';
		}
	?>
</div>
