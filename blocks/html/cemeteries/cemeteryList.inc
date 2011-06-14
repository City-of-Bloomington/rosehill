<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
?>
<div class="interfaceBox">
	<h2>
		<?php
			if (userIsAllowed('Cemeteries')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/cemeteries/addCemetery.php';\">
					Add
				</button>
				";
			}
		?>
		Cemeteries
	</h2>
	<ul><?php
			foreach ($this->cemeteryList as $cemetery) {
				$editButton = '';
				if (userIsAllowed('Cemeteries')) {
					$url = new URL(BASE_URL.'/cemeteries/updateCemetery.php');
					$url->cemetery_id = $cemetery->getId();
					$editButton = "
					<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
						Edit
					</button>
					";
				}
				$name = View::escape($cemetery->getName());
				echo "
				<li>$editButton
					<a href=\"{$cemetery->getURL()}\">$name</a>
				</li>
				";
			}
		?>
	</ul>
</div>
<?php include APPLICATION_HOME.'/blocks/html/serviceButtons.inc'; ?>
