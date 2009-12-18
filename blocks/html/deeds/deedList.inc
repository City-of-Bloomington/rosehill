<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<div class="interfaceBox">
	<h1>
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
	</h1>
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
					$url->id = $deed->getId();
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
						Edit
					</button>
					";
				}
				$section = View::escape($deed->getSection());
				$lot = View::escape($deed->getLot());
				$owner1 = View::escape($deed->getOwner(1));
				$owner2 = View::escape($deed->getOwner(2));
				$date = $deed->getIssueDate('n/j/Y');
				echo "
				<tr><td>$editButton</td>
					<td>{$deed->getSection()}, {$deed->getLot()}</td>
					<td>
				";
			}
		?>
		</tbody>
	</table>
</div>