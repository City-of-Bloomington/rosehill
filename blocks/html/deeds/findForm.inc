<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<form method="get" action="<?php echo BASE_URI; ?>/deeds">
	<?php
		// Whether you're searching for deeds or interments, the fields
		// the user enters are the same
		include APPLICATION_HOME.'/blocks/html/interments/partials/findFields.inc';
	?>
	<div>
		<button type="submit" class="search">Search</button>
	</div>
</form>
