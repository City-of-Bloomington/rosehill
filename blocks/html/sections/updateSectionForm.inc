<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Section $this->section
 */
?>
<h1>Update Section</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Section Info</legend>
		<input name="section_id" type="hidden" value="<?php echo $this->section->getId(); ?>" />
		<table>

			<tr><td><label for="code" class="required">Code</label></td>
				<td><input name="code" id="code" value="<?php echo View::escape($this->section->getCode()); ?>" />
				</td>
			</tr>

			<tr><td><label for="name">Name</label></td>
				<td><input name="name" id="name" value="<?php echo View::escape($this->section->getName()); ?>" />
				</td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel"
				onclick="document.location.href='<?php echo $this->section->getCemetery()->getURL(); ?>';">
			Cancel
		</button>
	</fieldset>
</form>