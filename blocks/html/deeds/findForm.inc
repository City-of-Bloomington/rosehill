<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<form method="get" action="<?php echo BASE_URL; ?>/deeds">
	<fieldset><legend>Search Deeds</legend>
		<table>
			<tr><td><label for="section">Section</label></td>
				<td><input name="section" id="section" size="3"
							value="<?php echo isset($_GET['section']) ? View::escape($_GET['section']) : ''; ?>" />
					<label for="lot">Lot</label>
					<input name="lot" id="lot" size="3"
							value="<?php echo isset($_GET['lot']) ? View::escape($_GET['lot']) : ''; ?>" />
				</td>
			</tr>

			<tr><td><label for="firstname">Owner</label></td>
				<td><label for="firstname">Firstname</label>
					<input name="firstname" id="firstname"
							value="<?php echo isset($_GET['firstname']) ? View::escape($_GET['firstname']) : ''; ?>" />
					<label for="lastname">Lastname</label>
					<input name="lastname" id="lastname"
							value="<?php echo isset($_GET['lastname']) ? View::escape($_GET['lastname']) : ''; ?>" />
				</td>
			</tr>

			<tr><td><label for="cemetery_id">Cemetery</label></td>
				<td><select name="cemetery_id" id="cemetery_id">
						<option></option>
						<?php
							$list = new CemeteryList();
							$list->find();
							foreach ($list as $cemetery) {
								$name = View::escape($cemetery->getName());
								$selected = (isset($_GET['cemetery']) && $_GET['cemetery']==$name)
											? 'selected="selected"'
											: '';
								echo "<option value=\"{$cemetery->getId()}\">$name</option>";
							}
						?>
					</select>
				</td>
			</tr>
		</table>

		<button type="submit" class="search">Search</button>
		<button type="button" onclick="document.location.href='<?php echo BASE_URL; ?>/deeds';">
			Clear
		</button>
	</fieldset>
</form>