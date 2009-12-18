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
			if (userIsAllowed('Cemeteries')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/cemeteries/addCemetery.php';\">
					Add
				</button>
				";
			}
		?>
		Cemeteries
	</h1>
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
				echo "<li>$editButton $name</li>";
			}
		?>
	</ul>
</div>