<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
?>
<h2>Update Cemetery</h2>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>" enctype="multipart/form-data">
	<fieldset><legend>Cemetery Info</legend>
		<input name="cemetery_id" type="hidden" value="<?php echo $this->cemetery->getId(); ?>" />
		<table>

			<tr><td><label for="cemetery-name" class="required">Name</label></td>
				<td><input name="cemetery[name]" id="cemetery-name"
							value="<?php echo View::escape($this->cemetery->getName()); ?>" />
				</td>
			</tr>
			<tr><td><label for="cemetery-googleMapURL">Google Map URL</label></td>
				<td><input name="cemetery[googleMapURL]" id="cemetery-googleMapURL"
						value="<?php echo View::escape($this->cemetery->getGoogleMapURL()); ?>" />
				</td>
			</tr>

		</table>

		<button type="submit" class="submit">Submit</button>
		<button type="button" class="cancel" onclick="document.location.href='<?php echo BASE_URL; ?>/cemeteries';">
			Cancel
		</button>
	</fieldset>
	<fieldset><legend>Maps</legend>
		<div>
			<label for="map">Map</lable>
			<input type="file" name="map" id="map" />
		</div>
		<?php
			$map = $this->cemetery->getMap();
			if ($map) {
				echo "<div><img src=\"$map\" /></div>";
			}
		?>
		<div>
			<label for="thumbnail">Thumbnail</lable>
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