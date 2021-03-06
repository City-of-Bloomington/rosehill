<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Interment $this->interment
 */
$fullname = View::escape($this->interment->getFullname());
echo "<h2>$fullname</h2>";

$date = $this->interment->getDeceasedDate('n/j/Y');
$birth = View::escape($this->interment->getBirthPlace());
$residence = View::escape($this->interment->getLastResidence());
$cemetery = View::escape($this->interment->getCemetery());
$age = View::escape($this->interment->getAge());
$gender = View::escape($this->interment->getSex());

$section_lot = array();
if ($this->interment->getSection()) {
	$section_lot[] = $this->interment->getSection();
}
if ($this->interment->getLot()) {
	$section_lot[] = $this->interment->getLot();
}
$section_lot = View::escape(implode(', ',$section_lot));
?>
<table>
	<tr><th>Date of Death </th>
		<td><?php echo $date; ?></td>
	</tr>
	<tr><th>Last Residence </th>
		<td><?php echo $residence; ?></td>
	</tr>
	<tr><th>Place of Birth </th>
		<td><?php echo $birth; ?></td>
	</tr>
	<tr><th>Age </th>
		<td><?php echo $age; ?></td>
	</tr>
	<tr><th>Gender </th>
		<td><?php echo $gender; ?></td>
	</tr>
	<tr><th>Cemetery </th>
		<td><?php echo $cemetery; ?></td>
	</tr>
	<tr><th>Section and Lot </th>
		<td><?php echo $section_lot; ?></td>
	</tr>
</table>
<?php
	if ($this->interment->getLot()) {
		$url = new URL(BASE_URL.'/interments');
		$url->cemetery_id = $this->interment->getCemetery_id();
		$url->section_id = $this->interment->getSection_id();
		$url->lot = $this->interment->getLot();
		echo "
		<div>
			<button type=\"button\" onclick=\"document.location.href='$url';\">
				View All Interments for this Lot
			</button>
		</div>
		";
	}
	if (userIsAllowed('Interments')) {
		$notes = View::escape($this->interment->getNotes());
		echo "<p>$notes</p>";
	}
?>