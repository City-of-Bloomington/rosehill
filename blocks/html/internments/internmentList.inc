<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param InternmentList $this->internmentList
 */
?>
<div class="interfaceBox">
	<h1>
		<?php
			if (userIsAllowed('Internments')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/internments/addInternment.php';\">
					Add
				</button>
				";
			}
		?>
		Internments
	</h1>
	<table>
		<thead>
			<tr><th></th>
				<th>Last Name</th>
				<th>First Name</th>
				<th>Date of Death</th>
				<th>Cemetery</th>
				<th>Section &amp; Lot</th>
			</tr>
		</thead>
		<tbody>
		<?php
			foreach ($this->internmentList as $internment) {
				$editButton = '';
				if (userIsAllowed('Internments')) {
					$url = new URL(BASE_URL.'/internments/updateInternment.php');
					$url->internment_id = $internment->getId();
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
						Edit
					</button>
					";
				}
				$last = View::escape($internment->getLastname());
				$first = View::escape($internment->getFirstname());
				$date = $internment->getDeceasedDate('n/j/Y');
				$cemetery = View::escape($internment->getCemetery());

				$section_lot = array();
				if ($internment->getSection()) {
					$section_lot[] = $internment->getSection();
				}
				if ($internment->getLot()) {
					$section_lot[] = $internment->getLot();
				}
				$section_lot = View::escape(implode(', ',$section_lot));

				echo "
				<tr><td>$editButton</td>
					<td>$last</td>
					<td>$first</td>
					<td>$date</td>
					<td>$cemetery</td>
					<td>$section_lot</td>
				</tr>
				";
			}
		?>
		</tbody>
	</table>
</div>
<?php
	if ($this->internmentList->getPaginator()) {
		$pageNavigation = new Block('pageNavigation.inc',
									array('pages'=>$this->internmentList->getPaginator()->getPages()));
		echo $pageNavigation->render('html');
	}
?>