<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h2>Add Person</h2>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Personal Info</legend>
		<table>

			<tr><td><label for="person-firstname" class="required">Firstname</label></td>
				<td><input name="person[firstname]" id="person-firstname" />
				</td>
			</tr>

			<tr><td><label for="person-lastname" class="required">Lastname</label></td>
				<td><input name="person[lastname]" id="person-lastname" />
				</td>
			</tr>

			<tr><td><label for="person-email">Email</label></td>
				<td><input name="person[email]" id="person-email" />
				</td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/people';">
			Cancel
		</button>
	</fieldset>
</form>
