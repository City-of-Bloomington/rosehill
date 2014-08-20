<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
use Application\Models\CemeteriesTable;
use Application\Models\SectionsTable;
use Blossom\Classes\View;

$lastname  = isset($_GET['lastname'])  ? View::escape($_GET['lastname'])  : '';
$firstname = isset($_GET['firstname']) ? View::escape($_GET['firstname']) : '';
$lot       = isset($_GET['lot'])       ? View::escape($_GET['lot'])       : '';
?>
<table>
	<tr><td><label for="lastname">Last Name</label></td>
		<td><input name="lastname" id="lastname" value="<?php echo $lastname; ?>" /></td>
	</tr>
	<tr><td><label for="firstname">First Name</label></td>
		<td><input name="firstname" id="firstname" value="<?php echo $firstname; ?>" /></td>
	</tr>
	<tr><td><label for="cemetery_id">Cemetery</label></td>
		<td><select name="cemetery_id" id="cemetery_id"
				onchange="COB.populateSections(this.options[this.selectedIndex].value,'section_id','<?php echo BASE_URL; ?>')">
				<option value="">Any Cemetery</option>
				<?php
                    $table = new CemeteriesTable();
                    $cemeteries = $table->find();
					foreach ($cemeteries as $cemetery) {
						$name = View::escape($cemetery->getName());
						$selected = (isset($_GET['cemetery_id']) && $_GET['cemetery_id']==$cemetery->getId())
								? 'selected="selected"'
								: '';
						echo "<option value=\"{$cemetery->getId()}\" $selected>$name</option>";
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
                        $table = new SectionsTable();
                        $sections = $table->find(['cemetery_id'=>$_GET['cemetery_id']]);
						foreach ($sections as $section) {
							$name = $section->getName()
									? View::escape($section->getName())
									: View::escape($section->getCode());
							$selected = isset($_GET['section_id']) && $section->getId()==$_GET['section_id']
									? 'selected="selected"'
									: '';
							echo "<option value=\"{$section->getId()}\" $selected>$name</option>";
						}
					}
				?>
			</select>
			<label for="lot">Lot</label>
			<input name="lot" id="lot" size="3" value="<?php echo $lot; ?>" />
		</td>
	</tr>
</table>
