<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h2>Add Deed</h2>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Deed Info</legend>
		<table>
			<tr><td><label for="deed-cemetery_id">Cemetery</label></td>
				<td><select name="deed[cemetery_id]" id="deed-cemetery_id"
						onchange="COB.populateSections(this.options[this.selectedIndex].value,'deed-section_id','<?php echo BASE_URL; ?>')">
						<option></option>
						<?php
							$list = new CemeteryList();
							$list->find();
							foreach ($list as $cemetery) {
								$name = View::escape($cemetery->getName());
								echo "<option value=\"{$cemetery->getId()}\">$name</option>";
							}
						?>
					</select>
				</td>
			</tr>
			<tr><td><label for="deed-section_id">Section</label></td>
				<td><select name="deed[section_id]" id="deed-section_id">
						<option></option>
					</select>
					<label for="deed-lot">Lot</label>
					<input name="deed[lot]" id="deed-lot" />
				</td>
			</tr>
			<tr><td><label for="deed-issueDate">Date Issued</label></td>
				<td><input name="deed[issueDate]" size="10" /></td>
			</tr>
			<tr><td><label for="deed-notes">notes</label></td>
				<td><textarea name="deed[notes]" id="deed-notes" rows="3" cols="60"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset><legend>Owner 1</legend>
		<table>
			<tr><td><label for="deed-firstname1">First</label></td>
				<td><input name="deed[firstname1]" id="deed-firstname1" /></td>
			</tr>
			<tr><td><label for="deed-middleInitial1">MI</label></td>
				<td><input name="deed[middleInitial1]" id="deed-middleInitial1" size="1" maxlength="1" />
				</td>
			</tr>
			<tr><td><label for="deed-lastname1">Last</label></td>
				<td><input name="deed[lastname1]" id="deed-lastname1" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset><legend>Owner 2</legend>
		<table>
			<tr><td><label for="deed-firstname2">First</label></td>
				<td><input name="deed[firstname2]" id="deed-firstname2" /></td>
			</tr>
			<tr><td><label for="deed-middleInitial2">MI</label></td>
				<td><input name="deed[middleInitial2]" id="deed-middleInitial2" size="1" maxlength="1" />
				</td>
			</tr>
			<tr><td><label for="deed-lastname2">Last</label></td>
				<td><input name="deed[lastname2]" id="deed-lastname2" /></td>
			</tr>
		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/deeds';">
			Cancel
		</button>
	</fieldset>
</form>