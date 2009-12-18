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
				<td><input name="section" id="section" size="3" />
				</td>
			</tr>

			<tr><td><label for="lot">Lot</label></td>
				<td><input name="lot" id="lot" size="3" />
				</td>
			</tr>

			<tr><td><label for="lastname">Lastname</label></td>
				<td><input name="lastname" id="lastname" />
				</td>
			</tr>

			<tr><td><label for="firstname">Firstname</label></td>
				<td><input name="firstname" id="firstname" />
				</td>
			</tr>

			<tr><td><label for="middleInitial">Middle Initial</label></td>
				<td><input name="middleInitial" id="middleInitial" />
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
								echo "<option value=\"{$cemetery->getId()}\">$name</option>";
							}
						?>
					</select>
				</td>
			</tr>
		</table>

		<button type="submit" class="search">Search</button>
	</fieldset>
</form>