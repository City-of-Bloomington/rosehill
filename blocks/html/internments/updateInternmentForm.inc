<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Internment $this->internment
 */
?>
<h1>Update Internment</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Internment Info</legend>
		<input name="internment_id" type="hidden" value="<?php echo $this->internment->getId(); ?>" />
		<table>

			<tr><td><label for="internment-section">Section</label></td>
				<td><input name="internment[section]" id="internment-section"
						size="5" maxlength="5"
						value="<?php echo View::escape($this->internment->getSection()); ?>" />
					<label for="internment-lot">Lot</label>
					<input name="internment[lot]" id="internment-lot"
						size="5" maxlength="5"
						value="<?php echo View::escape($this->internment->getLot()); ?>" />
				</td>
			</tr>

			<tr><td><label for="internment-book">Book</label></td>
				<td><input name="internment[book]" id="internment-book"
						size="4" maxlength="4"
						value="<?php echo View::escape($this->internment->getBook()); ?>" />
					<label for="internment-pageNumber">Page</label>
					<input name="internment[pageNumber]" id="internment-pageNumber"
						size="5" maxlength="5"
						value="<?php echo View::escape($this->internment->getPageNumber()); ?>" />
				</td>
			</tr>
			<tr><td><label for="internment-firstname">Name</label></td>
				<td><label for="internment-firstname">First</label>
					<input name="internment[firstname]" id="internment-firstname"
						value="<?php echo View::escape($this->internment->getFirstname()); ?>" />
					<label for="internment-middleInitial">MI</label>
					<input name="internment[middleInitial]" id="internment-middleInitial"
						value="<?php echo View::escape($this->internment->getMiddleInitial()); ?>" />
					<label for="internment-lastname">Last</label>
					<input name="internment[lastname]" id="internment-lastname"
						value="<?php echo View::escape($this->internment->getLastname()); ?>" />
				</td>
			</tr>


			<tr><td><label for="internment-birthPlace">Place of Birth</label></td>
				<td><input name="internment[birthPlace]" id="internment-birthPlace"
						value="<?php echo View::escape($this->internment->getBirthPlace()); ?>" />
				</td>
			</tr>

			<tr><td><label for="internment-lastResidence">Last Residence</label></td>
				<td><input name="internment[lastResidence]" id="internment-lastResidence"
						value="<?php echo View::escape($this->internment->getLastResidence()); ?>" />
				</td>
			</tr>

			<tr><td><label for="internment-deceasedDate">Date of Death</label></td>
				<td><input name="internment[deceasedDate]" id="internment-deceasedDate"
						size="10" value="<?php echo $this->internment->getDeceasedDate('n/j/Y'); ?>" />
					<label for="internment-age">Age</label>
					<input name="internment[age]" id="internment-age"
						size="3" maxlength="3"
						value="<?php echo View::escape($this->internment->getAge()); ?>" />
					<label for="internment-sex">Gender</label>
					<select name="internment[sex]" id="internment-sex">
						<option></option>
						<option value="M" <?php echo $this->internment->getSex()=='M' ? 'selected="selected"' : ''; ?>>
							M
						</option>
						<option value="F" <?php echo $this->internment->getSex()=='F' ? 'selected="selected"' : ''; ?>>
							F
						</option>
					</select>
				</td>
			</tr>

			<tr><td><label for="internment-cemetery_id">Cemetery</label></td>
				<td><select name="internment[cemetery_id]" id="internment-cemetery_id">
						<option></option>
						<?php
							$list = new CemeteryList();
							$list->find();
							foreach ($list as $cemetery) {
								$name = View::escape($cemetery->getName());
								$selected = $this->internment->getCemetery_id()==$cemetery->getId()
											? 'selected="selected"'
											: '';
								echo "<option value=\"{$cemetery->getId()}\" $selected>$name</option>";
							}
						?>
					</select>
				</td>
			</tr>

			<tr><td><label for="internment-notes">Notes</label></td>
				<td><textarea name="internment[notes]" id="internment-notes" rows="3" cols="60"><?php echo View::escape($this->internment->getNotes()); ?></textarea>
				</td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/internments';">
			Cancel
		</button>
	</fieldset>
</form>