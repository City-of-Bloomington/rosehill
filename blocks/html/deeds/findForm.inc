<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<form method="get" action="<?php echo BASE_URL; ?>/deeds">
	<fieldset><legend>Search Deeds</legend>
		<table>
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
				<td><select name="cemetery_id" id="cemetery_id"
						onchange="COB.populateSections(this.options[this.selectedIndex].value,'section_id','<?php echo BASE_URL; ?>')">
						<option value="">Any Cemetery</option>
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
			<tr><td><label for="section_id">Section</label></td>
				<td><select name="section_id" id="section_id">
						<option value="">Any Section</option>
						<?php
							if (isset($_GET['cemetery_id'])) {
								try {
									$cemetery = new Cemetery($_GET['cemetery_id']);
									foreach ($cemetery->getSections() as $section) {
										$name = $section->getName()
											? View::escape($section->getName())
											: View::escape($section->getCode());
										$selected = (isset($_GET['section_id']) && $_GET['section_id']==$section->getId())
												? 'selected="selected"'
												: '';
										echo "<option value=\"{$section->getId()}\" $selected>$name</option>";
									}
								}
								catch (Exception $e) {
								}
							}
							else {
								$id = View::escape($_GET['section_id']);
								echo "<option value=\"$id\" selected=\"selected\">$id</option>";
							}
						?>
					</select>
					<label for="lot">Lot</label>
					<input name="lot" id="lot" size="3"
							value="<?php echo isset($_GET['lot']) ? View::escape($_GET['lot']) : ''; ?>" />
				</td>
			</tr>
		</table>

		<button type="submit" class="search">Search</button>
		<button type="button" onclick="document.location.href='<?php echo BASE_URL; ?>/deeds';">
			Clear
		</button>
	</fieldset>
</form>