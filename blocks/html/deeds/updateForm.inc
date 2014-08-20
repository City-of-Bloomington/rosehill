<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 @param Deed $this->deed
 */
use Application\Models\CemeteriesTable;
use Blossom\Classes\View;

$this->template->addToAsset('scripts', YUI.'/yui/yui-min.js');
$this->template->addToAsset('scripts', BASE_URI.'/js/functions.js');

$fields = [
    'cemetery_id', 'section_id', 'lot',
    'firstname1', 'firstname2', 'middleInitial1', 'middleInitial2', 'lastname1', 'lastname2',
    'notes'
];
foreach ($fields as $f) {
    $get = 'get'.ucfirst($f);
    $$f = View::escape($this->deed->$get());
}

$title = $this->deed->getId() ? $this->translate('edit_deed') : $this->translate('add_deed');
?>
<h2><?php echo $title; ?></h2>
<form method="post" action="<?php echo BASE_URI; ?>/deeds/update">
	<fieldset><legend><?php echo $this->translate('info_deed'); ?></legend>
		<input name="deed_id" type="hidden" value="<?php echo $this->deed->getId(); ?>" />
		<table>
			<tr><td><label for="cemetery_id">Cemetery</label></td>
				<td><select name="cemetery_id" id="cemetery_id"
						onchange="COB.populateSections(this.options[this.selectedIndex].value,'section_id','<?php echo BASE_URL; ?>')">
						<option></option>
						<?php
                            $table = new CemeteriesTable();
							$list = $table->find();
							foreach ($list as $cemetery) {
								$name = View::escape($cemetery->getName());
								$selected = $cemetery->getId()==$cemetery_id
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
						<option></option>
						<?php
							if ($this->deed->getSection_id()) {
								foreach ($this->deed->getCemetery()->getSections() as $section) {
									$name = View::escape($section->getCode());
									$selected = $section->getId()==$section_id
											? 'selected="selected"'
											: '';
									echo "<option value=\"{$section->getId()}\" $selected>$name</option>";
								}
							}
						?>
					</select>
					<label for="lot">Lot</label>
					<input name="lot" id="deed-lot" size="3" value="<?php echo $lot; ?>" />
				</td>
			</tr>
			<tr><td><label for="issueDate">Date Issued</label></td>
				<td><input name="issueDate" size="10" value="<?php echo $this->deed->getIssueDate('n/j/Y'); ?>" /></td>
			</tr>
			<tr><td><label for="notes">notes</label></td>
				<td><textarea name="notes" id="notes" rows="3" cols="60"><?php echo $notes; ?></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset><legend>Owner 1</legend>
		<table>
			<tr><td><label for="firstname1">First</label></td>
				<td><input name="firstname1" id="firstname1" value="<?php echo $firstname1; ?>" /></td>
			</tr>
			<tr><td><label for="middleInitial1">MI</label></td>
				<td><input name="middleInitial1" id="middleInitial1" value="<?php echo $middleInitial1; ?>" /></td>
			</tr>
			<tr><td><label for="lastname1">Last</label></td>
				<td><input name="lastname1" id="lastname1" value="<?php echo $lastname1; ?>" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset><legend>Owner 2</legend>
		<table>
			<tr><td><label for="firstname2">First</label></td>
				<td><input name="firstname2" id="firstname2" value="<?php echo $firstname2; ?>" /></td>
			</tr>
			<tr><td><label for="middleInitial2">MI</label></td>
				<td><input name="middleInitial2" id="middleInitial2" value="<?php echo $middleInitial2; ?>" /></td>
			</tr>
			<tr><td><label for="lastname2">Last</label></td>
				<td><input name="lastname2" id="lastname2" value="<?php echo $lastname2; ?>" /></td>
			</tr>
		</table>

		<?php
            $h = $this->template->getHelper('saveAndCancelButtons');
            echo $h->saveAndCancelButtons(BASE_URI.'/deeds');
		?>
	</fieldset>
</form>
