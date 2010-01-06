<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param URL $this->return_url
 */
$return_url = isset($this->return_url) ? $this->return_url : BASE_URL.'/interments';
?>
<h1>Add Interment</h1>
<form method="post" action="<?php echo BASE_URL; ?>/interments/addInterment.php">
	<fieldset><legend>Interment Info</legend>
		<input type="hidden" name="return_url" value="<?php echo $return_url; ?>" />

		<table>
			<tr><td><label for="interment-cemetery_id" class="required">Cemetery</label></td>
				<td><select name="interment[cemetery_id]" id="interment-cemetery_id"
						onchange="populateSections(this.options[this.selectedIndex].value)">
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

			<tr><td><label for="interment-section_id">Section</label></td>
				<td><select name="interment[section_id]" id="interment-section_id">
						<option></option>
					</select>
					<label for="interment-lot">Lot</label>
					<input name="interment[lot]" id="interment-lot" size="5" maxlength="5" />
				</td>
			</tr>

			<tr><td><label for="interment-book">Book</label></td>
				<td><input name="interment[book]" id="interment-book" size="4" maxlength="4" />
					<label for="interment-pageNumber">Page</label>
					<input name="interment[pageNumber]" id="interment-pageNumber" size="5" maxlength="5" />
				</td>
			</tr>

			<tr><td><label for="interment-firstname">First</label></td>
				<td><input name="interment[firstname]" id="interment-firstname" /></td>
			</tr>
			<tr><td><label for="interment-middleInitial">MI</label></td>
				<td><input name="interment[middleInitial]" id="interment-middleInitial" size="1" maxlength="1" />
				</td>
			</tr>
			<tr><td><label for="interment-lastname">Last</label></td>
				<td><input name="interment[lastname]" id="interment-lastname" /></td>
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
		</table>

		<div><label for="interment-notes">Notes</label></div>
		<div><textarea name="interment[notes]" id="interment-notes" rows="3" cols="60"></textarea></div>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo $return_url; ?>';">
			Cancel
		</button>
	</fieldset>
</form>
<script type="text/javascript">
function populateSections(cemetery_id) {
	var url = '<?php echo BASE_URL; ?>/cemeteries/viewCemetery.php?format=json;cemetery_id=' + cemetery_id;

	YAHOO.util.Connect.asyncRequest('GET',url,{
		success : function (o) {
			var select = document.getElementById('interment-section_id');
			select.innerHTML = '';
			select.appendChild(document.createElement('option'));

			var sections = YAHOO.lang.JSON.parse(o.responseText).sections
			for (i in sections) {
				var option = document.createElement('option');
				option.setAttribute('value',sections[i].id);
				option.appendChild(document.createTextNode(sections[i].code));
				select.appendChild(option);
			}
		},

		failure : function (o) {
		}
	});
}
</script>
