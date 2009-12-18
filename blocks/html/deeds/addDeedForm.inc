<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Add Deed</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Deed Info</legend>
		<table>

			<tr><td><label for="deed-section">Section</label></td>
				<td><input name="deed[section]" id="deed-section" size="3" />
					<label for="deed-lot">Lot</label>
					<input name="deed[lot]" id="deed-lot" />
				</td>
			</tr>


			<tr><td><label for="deed-firstname1">Owner 1</label></td>
				<td><label for="deed-firstname1">First</label>
					<input name="deed[firstname1]" id="deed-firstname1" />
					<label for="deed-middleInitial1">MI</label>
					<input name="deed[middleInitial1]" id="deed-middleInitial1" size="1" maxlength="1" />
					<label for="deed-lastname1">Last</label>
					<input name="deed[lastname1]" id="deed-lastname1" />
				</td>
			</tr>

			<tr><td><label for="deed-firstname2">Owner 2</label></td>
				<td><label for="deed-firstname2">First</label>
					<input name="deed[firstname2]" id="deed-firstname2" />
					<label for="deed-middleInitial2">MI</label>
					<input name="deed[middleInitial2]" id="deed-middleInitial2" size="1" maxlength="1" />
					<label for="deed-lastname2">Last</label>
					<input name="deed[lastname2]" id="deed-lastname2" />
				</td>
			</tr>

			<tr><td><label for="deed-issueDate">Date Issued</label></td>
				<td><input name="deed[issueDate]" size="10" /></td>
			</tr>

			<tr><td><label for="deed-cemetery_id">Cemetery</label></td>
				<td><select name="deed[cemetery_id]" id="deed-cemetery_id">
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

			<tr><td><label for="deed-notes">notes</label></td>
				<td><textarea name="deed[notes]" id="deed-notes" rows="3" cols="60"></textarea>
				</td>
			</tr>



		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/deeds';">
			Cancel
		</button>
	</fieldset>
</form>