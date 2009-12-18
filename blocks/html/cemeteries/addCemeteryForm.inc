<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Add Cemetery</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Cemetery Info</legend>
		<table>

			<tr><td><label for="cemetery-name" class="required">Name</label></td>
				<td><input name="cemetery[name]" id="cemetery-name" />
				</td>
			</tr>
			<tr><td><label for="cemetery-googleMapURL">Google Map URL</label></td>
				<td><input name="cemetery[googleMapURL]" id="cemetery-googleMapURL" /></td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/cemeteries';">
			Cancel
		</button>
	</fieldset>
</form>