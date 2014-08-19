<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
$sections = [];
foreach ($this->cemetery->getSections() as $section) {
    $sections[] = [
        'id'   => $section->getId(),
        'code' => $section->getCode(),
        'name' => $section->getName()
    ];
}

$cemetery = [
    'id'      => $this->cemetery->getId(),
    'name'    => $this->cemetery->getName(),
    'sections'=> $sections
];

echo json_encode($cemetery);
