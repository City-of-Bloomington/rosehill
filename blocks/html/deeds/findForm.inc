<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<form method="get" action="<?php echo BASE_URL; ?>/deeds">
	<?php
		// Whether you're searching for deeds or interments, the fields
		// the user enters are the same
		include APPLICATION_HOME.'/blocks/html/interments/partials/findFields.inc';
	?>
	<div>
		<button type="submit" class="search">Search</button>
	</div>
</form>