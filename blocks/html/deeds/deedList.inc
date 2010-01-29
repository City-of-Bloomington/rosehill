<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param DeedList $this->deedList
 */
?>
<div class="interfaceBox">
	<h2>
		<?php
			if (userIsAllowed('Deeds')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/deeds/addDeed.php';\">
					Add
				</button>
				";
			}
		?>
		Deeds
	</h2>
	<table>
		<thead>
			<tr><th></th>
				<th>Section, Lot</th>
				<th>Owner 1</th>
				<th>Owner 2</th>
				<th>Issue Date</th>
				<th>Cemetery</th>
			</tr>
		</thead>
		<tbody>
		<?php
			foreach ($this->deedList as $deed) {
				$editButton = '';
				if (userIsAllowed('Deeds')) {
					$url = new URL(BASE_URL.'/deeds/updateDeed.php');
					$url->deed_id = $deed->getId();
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
						Edit
					</button>
					";

					$url = new URL(BASE_URL.'/deeds/deleteDeed.php');
					$url->deed_id = $deed->getId();
					$url->return_url = "http://$_SERVER[SERVER_NAME]$_SERVER[REQUEST_URI]";
					$deleteButton = "
					<button type=\"button\" class=\"delete\" onclick=\"COB.deleteConfirmation('$url');\">
						Delete
					</button>
					";
				}
				$section = View::escape($deed->getSection());
				$lot = View::escape($deed->getLot());
				$owner1 = View::escape($deed->getOwner(1));
				$owner2 = View::escape($deed->getOwner(2));
				$date = $deed->getIssueDate('n/j/Y');
				$cemetery = $deed->getCemetery() ? View::escape($deed->getCemetery()->getName()) : '';
				echo "
				<tr><td>$editButton $deleteButton</td>
					<td>{$deed->getSection()}, {$deed->getLot()}</td>
					<td>$owner1</td>
					<td>$owner2</td>
					<td>$date</td>
					<td>$cemetery</td>
				</tr>
				";
			}
		?>
		</tbody>
	</table>
</div>
<?php
	if ($this->deedList->getPaginator()) {
		$pageNavigation = new Block('pageNavigation.inc',
									array('pages'=>$this->deedList->getPaginator()->getPages()));
		echo $pageNavigation->render('html');
	}
?>