<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
use Application\Models\Person;
use Blossom\Classes\View;

$addButton = '';
if (Person::isAllowed('sections', 'edit')) {
    $helper = $this->template->getHelper('buttonLink');
    $addButton = $helper->buttonLink(
        BASE_URI.'/sections/update?cemetery_id='.$this->cemetery->getId(),
        $this->translate('add_section'),
        'add'
    );
}

$name = View::escape($this->cemetery->getName());
echo "
<h2>$name</h2>
<h3>{$this->translate(['section', 'sections', 2])} $addButton</h3>
<table>
	<thead>
		<tr><th></th>
			<th>{$this->translate('code')}</th>
			<th>{$this->translate('name')}</th>
		</tr>
	</thead>
	<tbody>
";
	foreach ($this->cemetery->getSections() as $section) {
		$editButton = '';
		if (Person::isAllowed('sections', 'edit')) {
            $editButton = $helper->buttonLink(
                BASE_URI.'/sections/update?section_id='.$section->getId(),
                 $this->translate('edit'),
                'edit'
            );
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
