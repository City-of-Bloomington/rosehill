<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
use Blossom\Classes\View;

$name         = View::escape($this->cemetery->getName());
$googleMapURL = View::escape($this->cemetery->getGoogleMapURL());

$title = $this->cemetery->getId() ? $this->translate('edit_cemetery') : $this->translate('add_cemetery');
?>
<h2><?php echo $title; ?></h2>
<form method="post" action="<?php echo BASE_URI; ?>/cemeteries/update" enctype="multipart/form-data">
	<fieldset><legend><?php echo $this->translate('info_cemetery'); ?></legend>
		<input name="cemetery_id" type="hidden" value="<?php echo $this->cemetery->getId(); ?>" />

		<table>
			<tr><td><label for="name" class="required">Name</label></td>
				<td><input name="name" id="name" value="<?php echo $name; ?>" /></td>
			</tr>
			<tr><td><label for="googleMapURL">Google Map URL</label></td>
				<td><input name="googleMapURL" id="googleMapURL" value="<?php echo $googleMapURL; ?>" /></td>
			</tr>
		</table>
		<?php
            $h = $this->template->getHelper('saveAndCancelButtons');
            echo $h->saveAndCancelButtons(BASE_URI.'/cemeteries');
		?>
	</fieldset>
	<fieldset><legend><?php echo $this->translate(['map', 'maps', 2]); ?></legend>
		<div>
			<label for="map"><?php echo $this->translate(['map', 'maps', 1]); ?></lable>
			<input type="file" name="map" id="map" />
		</div>
		<?php
			$map = $this->cemetery->getMap();
			if ($map) {
				echo "<div><img src=\"$map\" /></div>";
			}
		?>
		<div>
			<label for="thumbnail"><?php echo $this->translate('thumbnail'); ?></lable>
			<input type="file" name="thumbnail" id="thumbnail" />
		</div>
		<?php
			$map = $this->cemetery->getMap('thumbnail');
			if ($map) {
				echo "<div><img src=\"$map\" /></div>";
			}
		?>
	</fieldset>
</form>
