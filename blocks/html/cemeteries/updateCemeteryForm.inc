<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
?>
<h1>Update Cemetery</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Cemetery Info</legend>
		<input name="cemetery_id" type="hidden" value="<?php echo $this->cemetery->getId(); ?>" />
		<table>

			<tr><td><label for="cemetery-name" class="required">Name</label></td>
				<td><input name="cemetery[name]" id="cemetery-name"
							value="<?php echo View::escape($this->cemetery->getName()); ?>" />
				</td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/cemeterys';">
			Cancel
		</button>
	</fieldset>
</form>