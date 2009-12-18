<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param IntermentList $this->intermentList
 */
?>
<div class="interfaceBox">
	<h1>
		<?php
			if (userIsAllowed('Interments')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/interments/addInterment.php';\">
					Add
				</button>
				";
			}
		?>
		Interments
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
			foreach ($this->intermentList as $interment) {
				$editButton = '';
				if (userIsAllowed('Interments')) {
					$url = new URL(BASE_URL.'/interments/updateInterment.php');
					$url->interment_id = $interment->getId();
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
						Edit
					</button>
					";
				}
				$last = View::escape($interment->getLastname());
				$first = View::escape($interment->getFirstname());
				$date = $interment->getDeceasedDate('n/j/Y');
				$cemetery = View::escape($interment->getCemetery());

				$section_lot = array();
				if ($interment->getSection()) {
					$section_lot[] = $interment->getSection();
				}
				if ($interment->getLot()) {
					$section_lot[] = $interment->getLot();
				}
				$section_lot = View::escape(implode(', ',$section_lot));

				echo "
				<tr><td>$editButton</td>
					<td><a href=\"{$interment->getURL()}\">$last</a></td>
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
	if ($this->intermentList->getPaginator()) {
		$pageNavigation = new Block('pageNavigation.inc',
									array('pages'=>$this->intermentList->getPaginator()->getPages()));
		echo $pageNavigation->render('html');
	}
?>