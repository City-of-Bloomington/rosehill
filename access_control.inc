<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
use Zend\Permissions\Acl\Acl;
use Zend\Permissions\Acl\Role\GenericRole as Role;
use Zend\Permissions\Acl\Resource\GenericResource as Resource;

$ZEND_ACL = new Acl();
$ZEND_ACL->addRole(new Role('Anonymous'))
         ->addRole(new Role('Public'), 'Anonymous')
         ->addRole(new Role('Staff'), 'Public')
         ->addRole(new Role('Administrator'), 'Staff');

/**
 * Declare all the resources
 */
$ZEND_ACL->addResource(new Resource('index'));
$ZEND_ACL->addResource(new Resource('people'));
$ZEND_ACL->addResource(new Resource('users'));
$ZEND_ACL->addResource(new Resource('login'));

$ZEND_ACL->addResource(new Resource('deeds'));
$ZEND_ACL->addResource(new Resource('interments'));
$ZEND_ACL->addResource(new Resource('cemeteries'));
$ZEND_ACL->addResource(new Resource('sections'));

/**
 * Assign permissions to the resources
 */
$ZEND_ACL->allow(null,'login');

// Permissions for unauthenticated browsing
$ZEND_ACL->allow(null,
                array('index'),
                array('index'));

// Allow Staff to do stuff
$ZEND_ACL->allow('Staff',['deeds', 'interments']);
$ZEND_ACL->allow('Staff',
                ['people', 'cemeteries', 'sections'],
                ['index','view']);

// Administrator is allowed access to everything
$ZEND_ACL->allow('Administrator');
