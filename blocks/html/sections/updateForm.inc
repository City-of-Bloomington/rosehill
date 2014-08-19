<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Section $this->section
 */
use Blossom\Classes\View;

$code = View::escape($this->section->getCode());
$name = View::escape($this->section->getName());

$title = $this->section->getId() ? $this->translate('edit_section') : $this->translate('add_section');
?>
<h2><?php echo $title; ?></h2>
<form method="post" action="<?php echo BASE_URI; ?>/sections/update" enctype="multipart/form-data">
	<fieldset><legend><?php echo $this->translate('info_section'); ?></legend>
		<input name="section_id"  type="hidden" value="<?php echo $this->section->getId(); ?>" />
		<input name="cemetery_id" type="hidden" value="<?php echo $this->section->getCemetery_id(); ?>" />

		<table>
			<tr><td><label for="code" class="required"><?php echo $this->translate('code'); ?></label></td>
				<td><input name="code" id="code" value="<?php echo $code; ?>" /></td>
			</tr>

			<tr><td><label for="name"><?php echo $this->translate('name'); ?></label></td>
				<td><input name="name" id="name" value="<?php echo $name; ?>" /></td>
			</tr>
		</table>
		<?php
            $h = $this->template->getHelper('saveAndCancelButtons');
            echo $h->saveAndCancelButtons($this->section->getCemetery()->getUri());
		?>
	</fieldset>

	<fieldset><legend><?php echo $this->translate(['map', 'maps', 2]); ?></legend>
		<div>
			<label for="highlight_map"><?php echo $this->translate('map_highlight'); ?></lable>
			<input type="file" name="highlight_map" id="highlight_map" />
		</div>
		<?php
			$highlight = $this->section->getMap('highlight');
			if ($highlight) {
				echo "<div><img src=\"$highlight\" /></div>";
			}
		?>
		<div>
			<label for="zoom_map"><?php echo $this->translate('map_zoomed'); ?></lable>
			<input type="file" name="zoom_map" id="zoom_map" />
		</div>
		<?php
			$zoom = $this->section->getMap('zoom');
			if ($zoom) {
				echo "<div><img src=\"$zoom\" /></div>";
			}
		?>
	</fieldset>
</form>
