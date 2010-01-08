<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<h2>Add Cemetery</h2>
<form method="post" action="<?php echo $_SERVER['SCRIPT_NAME']; ?>" enctype="multipart/form-data">
	<fieldset><legend>Cemetery Info</legend>
		<table>

			<tr><td><label for="cemetery-name" class="required">Name</label></td>
				<td><input name="cemetery[name]" id="cemetery-name" />
				</td>
			</tr>
			<tr><td><label for="cemetery-googleMapURL">Google Map URL</label></td>
				<td><input name="cemetery[googleMapURL]" id="cemetery-googleMapURL" /></td>
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
		<div>
			<label for="thumbnail">Thumbnail</lable>
			<input type="file" name="thumbnail" id="thumbnail" />
		</div>
	</fieldset>
</form>