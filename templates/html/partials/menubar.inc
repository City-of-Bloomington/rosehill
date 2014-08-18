<?php
use Application\Models\Person;
?>
<nav>
<?php
    $a = '<a href="%s">%s</a>';

    $routes = [
        'interment' => 'interments',
        'cemetery'  => 'cemeteries',
        'deed'      => 'deeds',
        'person'    => 'people',
        'user'      => 'users'
    ];
    foreach ($routes as $singular=>$plural) {
        if (Person::isAllowed($plural, 'index')) {
            echo sprintf($a, BASE_URI.'/'.$plural, $this->_(["$singular", "$plural", 2]));
        }
    }
?>
</nav>
