/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.removeDialogTabs = 'image:advanced;link:advanced';
	config.toolbar = 'Full';
	config.toolbar_Full = [['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
                           ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
                           ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                            '/',
                           ['Link','Unlink','Anchor'],
                           ['Cut','Copy','Paste','PasteText','PasteFromWord','-'],
                           ['Image','Table','HorizontalRule','Smiley','SpecialChar'],
                            '/',
                           ['Styles','Format','Font','FontSize'],
                           ['TextColor','BGColor']]; 
};
