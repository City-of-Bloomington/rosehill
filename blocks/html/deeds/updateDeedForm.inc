<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Update Deed</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Deed Info</legend>
		<input name="id" type="hidden" value="<?php echo $this->deed->getId(); ?>" />
		<table>

			<tr><td><label for="deed-section">section</label></td>
				<td><input name="deed[section]" id="deed-section" value="<?php echo $this->deed->getSection(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-lot">lot</label></td>
				<td><input name="deed[lot]" id="deed-lot" value="<?php echo $this->deed->getLot(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-lastname1">lastname1</label></td>
				<td><input name="deed[lastname1]" id="deed-lastname1" value="<?php echo $this->deed->getLastname1(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-firstname1">firstname1</label></td>
				<td><input name="deed[firstname1]" id="deed-firstname1" value="<?php echo $this->deed->getFirstname1(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-middleInitial1">middleInitial1</label></td>
				<td><input name="deed[middleInitial1]" id="deed-middleInitial1" value="<?php echo $this->deed->getMiddleInitial1(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-lastname2">lastname2</label></td>
				<td><input name="deed[lastname2]" id="deed-lastname2" value="<?php echo $this->deed->getLastname2(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-firstname2">firstname2</label></td>
				<td><input name="deed[firstname2]" id="deed-firstname2" value="<?php echo $this->deed->getFirstname2(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-middleInitial2">middleInitial2</label></td>
				<td><input name="deed[middleInitial2]" id="deed-middleInitial2" value="<?php echo $this->deed->getMiddleInitial2(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-issueDate-mon">issueDate</label></td>
				<td><select name="deed[issueDate][mon]" id="deed-issueDate-mon">
						<option></option>
						<?php
							$issueDate = $this->deed->dateStringToArray($this->deed->getIssueDate());
							for ($i=1; $i<=12; $i++) {
								$selected = ($i==$issueDate['mon']) ? 'selected="selected"' : '';
								echo "<option $selected>$i</option>";
							}
						?>
					</select>
					<select name="deed[issueDate][mday]">
						<option></option>
						<?php
							for ($i=1; $i<=31; $i++) {
								$selected = ($i==$issueDate['mday']) ? 'selected="selected"' : '';
								echo "<option $selected>$i</option>";
							}
						?>
					</select>
					<input name="deed[issueDate][year]" id="deed-issueDate-year" size="4" maxlength="4" value="<?php echo $issueDate['year']; ?>" />
				</td>
			</tr>
			<tr><td><label for="deed-notes">notes</label></td>
				<td><textarea name="deed[notes]" id="deed-notes" rows="3" cols="60"><?php echo $this->deed->getNotes(); ?></textarea>
				</td>
			</tr>
				
			<tr><td><label for="deed-lot2">lot2</label></td>
				<td><input name="deed[lot2]" id="deed-lot2" value="<?php echo $this->deed->getLot2(); ?>" />
				</td>
			</tr>
				
			<tr><td><label for="deed-whiteoak">whiteoak</label></td>
				<td><input name="deed[whiteoak]" id="deed-whiteoak" value="<?php echo $this->deed->getWhiteoak(); ?>" />
				</td>
			</tr>
				
		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/deeds';">
			Cancel
		</button>
	</fieldset>
</form>