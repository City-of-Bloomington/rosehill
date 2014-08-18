<?php $startTime = microtime(1); ?>
<head>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<link rel="SHORTCUT ICON" href="/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="<?php echo BASE_URL; ?>/css/local/reset.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo BASE_URL; ?>/css/local/layouts/<?php echo $this->layout; ?>.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo BASE_URL; ?>/css/local/screen.css" />
    <title><?php echo isset($this->title) ? self::escape($this->title) : 'Cemeteries'; ?></title>
</head>
