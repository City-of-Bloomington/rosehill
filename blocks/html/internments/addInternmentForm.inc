<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Add Internment</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Internment Info</legend>
		<table>

			<tr><td><label for="internment-section">Section</label></td>
				<td><input name="internment[section]" id="internment-section"
						size="5" maxlength="5" />
					<label for="internment-lot">Lot</label>
					<input name="internment[lot]" id="internment-lot"
						size="5" maxlength="5" />
				</td>
			</tr>

			<tr><td><label for="internment-book">Book</label></td>
				<td><input name="internment[book]" id="internment-book"
						size="4" maxlength="4" />
					<label for="internment-pageNumber">Page</label>
					<input name="internment[pageNumber]" id="internment-pageNumber"
						size="5" maxlength="5" />
				</td>
			</tr>
			<tr><td><label for="internment-firstname">Name</label></td>
				<td><label for="internment-firstname">First</label>
					<input name="internment[firstname]" id="internment-firstname" />
					<label for="internment-middleInitial">MI</label>
					<input name="internment[middleInitial]" id="internment-middleInitial" />
					<label for="internment-lastname">Last</label>
					<input name="internment[lastname]" id="internment-lastname" />
				</td>
			</tr>


			<tr><td><label for="internment-birthPlace">Place of Birth</label></td>
				<td><input name="internment[birthPlace]" id="internment-birthPlace" />
				</td>
			</tr>

			<tr><td><label for="internment-lastResidence">Last Residence</label></td>
				<td><input name="internment[lastResidence]" id="internment-lastResidence" />
				</td>
			</tr>

			<tr><td><label for="internment-deceasedDate">Date of Death</label></td>
				<td><input name="internment[deceasedDate]" id="internment-deceasedDate" size="10" />
					<label for="internment-age">Age</label>
					<input name="internment[age]" id="internment-age" size="3" maxlength="3" />
					<label for="internment-sex">Gender</label>
					<select name="internment[sex]" id="internment-sex">
						<option></option>
						<option>M</option>
						<option>F</option>
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
								echo "<option value=\"{$cemetery->getId()}\">$name</option>";
							}
						?>
					</select>
				</td>
			</tr>

			<tr><td><label for="internment-notes">Notes</label></td>
				<td><textarea name="internment[notes]" id="internment-notes" rows="3" cols="60"></textarea>
				</td>
			</tr>


		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/internments';">
			Cancel
		</button>
	</fieldset>
</form>