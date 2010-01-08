<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
$addButton = '';
if (userIsAllowed('Sections')) {
	$url = BASE_URL.'/sections/addSection.php?cemetery_id='.$this->cemetery->getId();
	$addButton = "
	<button type=\"button\" class=\"add\" onclick=\"document.location.href='$url';\">
		Add a section
	</button>
	";
}

$name = View::escape($this->cemetery->getName());
echo "
<h1>$name</h1>
<h2>$addButton Sections</h2>
<table>
	<thead>
		<tr><th></th>
			<th>Code</th>
			<th>Name</th>
		</tr>
	</thead>
	<tbody>
";
	foreach ($this->cemetery->getSections() as $section) {
		$editButton = '';
		if (userIsAllowed('Sections')) {
			$url = BASE_URL.'/sections/updateSection.php?section_id='.$section->getId();
			$editButton = "
			<button type=\"button\" class=\"edit\" onclick=\"document.location.href='$url';\">
				Edit
			</button>
			";
		}
		$code = View::escape($section->getCode());
		$name = View::escape($section->getName());
		echo "
		<tr><td>$editButton</td>
			<td>$code</td>
			<td>$name</td>
			<td></td>
		</tr>
		";
	}
echo "
	</tbody>
</table>
";
include APPLICATION_HOME.'/blocks/html/serviceButtons.inc';
