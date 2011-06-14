<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
$sections = array();
foreach ($this->cemetery->getSections() as $section) {
	$code = addslashes($section->getCode());
	$name = addslashes($section->getName());
	$sections[] = "{\"id\":{$section->getId()},\"code\":\"$code\",\"name\":\"$name\"}";
}
$sections = implode(',',$sections);

$name = addslashes($this->cemetery->getName());
echo "
{	\"id\":{$this->cemetery->getId()},
	\"name\":\"$name\",
	\"sections\":[$sections]
}
";
