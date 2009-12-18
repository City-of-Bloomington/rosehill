<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h1>Search Burial Records</h1>
<form method="get" action="<?php echo BASE_URL; ?>/interments">
	<table>
		<tr><td><label for="lastname">Last Name</label></td>
			<td><input name="lastname" id="lastname"
					value="<?php echo isset($_GET['lastname']) ? View::escape($_GET['lastname']) : ''; ?>" />
			</td>
		</tr>
		<tr><td><label for="firstname">First Name</label></td>
			<td><input name="firstname" id="firstname"
					value="<?php echo isset($_GET['firstname']) ? View::escape($_GET['firstname']) : ''; ?>" />
			</td>
		</tr>
		<?php
			$cemeteries = new CemeteryList();
			$cemeteries->find();
			foreach ($cemeteries as $cemetery) {
				$name = View::escape($cemetery->getName());
				$fieldname = 'sections_'.$cemetery->getId();
				echo "
				<tr><td><label for=\"\">$name Sections</label></td>
					<td><select name=\"$fieldname\">
							<option value=\"\">All</option>
				";
					foreach ($cemetery->getSections() as $section) {
						$section = View::escape($section);
						$selected = (isset($_GET[$fieldname]) && $_GET[$fieldname]==$section)
									? 'selected="selected"'
									: '';
						echo "<option $selected>$section</option>";
					}
				echo "
						</select>
					</td>
				</tr>
				";
			}
		?>
	</table>
	<div>
		<button type="submit" class="search">Search</button>
	</div>
</form>