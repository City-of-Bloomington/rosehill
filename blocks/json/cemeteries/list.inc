<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Zend\Db\Result $this->cemeteries
 */
$cemeteries = [];
foreach ($this->cemeteries as $c) {
    $cemeteries[] = [
        'id'           => $c->getId(),
        'name'         => $c->getName(),
        'url'          => $c->getUrl(),
        'googleMapUrl' => $c->getGoogleMapURL()
    ];
}
echo json_encode($cemeteries);
