<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
?>
<h1>Add Section</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Section Info</legend>
		<input type="hidden" name="cemetery_id" value="<?php echo $this->cemetery->getId(); ?>" />
		<table>

			<tr><td><label for="code" class="required">Code</label></td>
				<td><input name="code" id="code" size="5" maxlength="5" /></td>
			</tr>

			<tr><td><label for="name">Name</label></td>
				<td><input name="name" id="name" /></td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel"
				onclick="document.location.href='<?php echo $this->cemetery->getURL(); ?>';">
			Cancel
		</button>
	</fieldset>
</form>