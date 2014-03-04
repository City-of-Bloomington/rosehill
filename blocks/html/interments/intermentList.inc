<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param IntermentList $this->intermentList
 * @param GET sort
 */
$previousSort = isset($_GET['sort']) ? $_GET['sort'] : 'deceasedDate';
$url = new URL($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']);
$url->purgeEmptyParameters();
$return_url = $url->__toString();
?>
<div class="interfaceBox">
	<h2>
		<?php
			if (userIsAllowed('Interments')) {
				$addURL = new URL(BASE_URL.'/interments/addInterment.php');
				$addURL->return_url = $return_url;
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='$addURL';\">
					Add
				</button>
				";
			}
		?>
		Interments
	</h2>
	<table>
		<thead>
			<tr><th></th>
				<th><?php
						$url->sort = $previousSort=='lastname' ? 'lastname desc' : 'lastname';
						echo "<a href=\"$url\" class=\"sorting\">Last Name</a>";
					?>
				</th>
				<th>M</th>
				<th><?php
						$url->sort = $previousSort=='firstname' ? 'firstname desc' : 'firstname';
						echo "<a href=\"$url\" class=\"sorting\">First Name</a>";
					?>
				</th>
				<th><?php
						$url->sort = $previousSort=='deceasedDate' ? 'deceasedDate desc' : 'deceasedDate';
						echo "<a href=\"$url\" class=\"sorting\">Date of Death</a>";
					?>
				</th>
				<th>Cemetery</th>
				<th>Section &amp; Lot</th>
			</tr>
		</thead>
		<tbody>
		<?php
			foreach ($this->intermentList as $interment) {
				$editButton = '';
				$deleteButton = '';
				if (userIsAllowed('Interments')) {
					$editURL = new URL(BASE_URL.'/interments/updateInterment.php');
					$editURL->interment_id = $interment->getId();
					$editURL->return_url = $return_url;
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$editURL';\">
						Edit
					</button>
					";
					$url = new URL(BASE_URL.'/interments/deleteInterment.php');
					$url->interment_id = $interment->getId();
					$url->return_url = "http://$_SERVER[SERVER_NAME]$_SERVER[REQUEST_URI]";
					$deleteButton = "
					<button type=\"button\" class=\"delete\" onclick=\"COB.deleteConfirmation('$url');\">
						Delete
					</button>
					";
				}
				$last = View::escape($interment->getLastname());
				$middle = View::escape($interment->getMiddleInitial());
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
				<tr><td>$editButton $deleteButton</td>
					<td><a href=\"{$interment->getURL()}\">$last</a></td>
					<td>$middle</td>
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

	include APPLICATION_HOME.'/blocks/html/serviceButtons.inc';
?>
