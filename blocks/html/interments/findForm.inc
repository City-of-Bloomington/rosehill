<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h2>Search Burial Records</h2>
<form method="get" action="<?php echo BASE_URL; ?>/interments">
	<?php
		// Whether you're searching for deeds or interments, the fields
		// the user enters are the same
		include APPLICATION_HOME.'/blocks/html/interments/partials/findFields.inc';
	?>
	<div>
		<button type="submit" class="search">Search</button>
	</div>
</form>