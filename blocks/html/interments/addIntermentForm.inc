<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Add Interment</h1>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>">
	<fieldset><legend>Interment Info</legend>
		<table>

			<tr><td><label for="interment-section">Section</label></td>
				<td><input name="interment[section]" id="interment-section"
						size="5" maxlength="5" />
					<label for="interment-lot">Lot</label>
					<input name="interment[lot]" id="interment-lot"
						size="5" maxlength="5" />
				</td>
			</tr>

			<tr><td><label for="interment-book">Book</label></td>
				<td><input name="interment[book]" id="interment-book"
						size="4" maxlength="4" />
					<label for="interment-pageNumber">Page</label>
					<input name="interment[pageNumber]" id="interment-pageNumber"
						size="5" maxlength="5" />
				</td>
			</tr>
			<tr><td><label for="interment-firstname">Name</label></td>
				<td><label for="interment-firstname">First</label>
					<input name="interment[firstname]" id="interment-firstname" />
					<label for="interment-middleInitial">MI</label>
					<input name="interment[middleInitial]" id="interment-middleInitial" />
					<label for="interment-lastname">Last</label>
					<input name="interment[lastname]" id="interment-lastname" />
				</td>
			</tr>


			<tr><td><label for="interment-birthPlace">Place of Birth</label></td>
				<td><input name="interment[birthPlace]" id="interment-birthPlace" />
				</td>
			</tr>

			<tr><td><label for="interment-lastResidence">Last Residence</label></td>
				<td><input name="interment[lastResidence]" id="interment-lastResidence" />
				</td>
			</tr>

			<tr><td><label for="interment-deceasedDate">Date of Death</label></td>
				<td><input name="interment[deceasedDate]" id="interment-deceasedDate" size="10" />
					<label for="interment-age">Age</label>
					<input name="interment[age]" id="interment-age" size="3" maxlength="3" />
					<label for="interment-sex">Gender</label>
					<select name="interment[sex]" id="interment-sex">
						<option></option>
						<option>M</option>
						<option>F</option>
					</select>
				</td>
			</tr>

			<tr><td><label for="interment-cemetery_id">Cemetery</label></td>
				<td><select name="interment[cemetery_id]" id="interment-cemetery_id">
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

			<tr><td><label for="interment-notes">Notes</label></td>
				<td><textarea name="interment[notes]" id="interment-notes" rows="3" cols="60"></textarea>
				</td>
			</tr>


		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/interments';">
			Cancel
		</button>
	</fieldset>
</form>