//
//
///*!
// * Color mode toggler for Bootstrap's docs (https://getbootstrap.com/)
// * Copyright 2011-2023 The Bootstrap Authors
// * Licensed under the Creative Commons Attribution 3.0 Unported License.
// */
//
(() => {
    'use strict'

    const getStoredTheme = () => localStorage.getItem('theme')
    const setStoredTheme = theme => localStorage.setItem('theme', theme)

    /**
     * Get Prefered Them
     * @returns {null|String}
     */ 
    const getPreferredTheme = () => {
        const storedTheme = getStoredTheme()
        if (storedTheme) {
            return storedTheme
        }

        return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
    }

    /**
     * Set The Them
     * @param {type} theme
     * @returns {undefined}
     */
    const setTheme = theme => {
        if (theme === 'auto' && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            document.documentElement.setAttribute('data-bs-theme', 'dark')
        } else {
            document.documentElement.setAttribute('data-bs-theme', theme)
        }
    }


    /**
     * Set Prefered Theme
     */
    setTheme(getPreferredTheme())



    const showActiveTheme = (theme, focus = false) => {
        const themeSwitcher = document.querySelector('#bd-theme');

        if (!themeSwitcher) {
            return
        }

        const themeSwitcherText = document.querySelector('#bd-theme-text')
        const activeThemeIcon = document.querySelector('.theme-icon-active');
        const btnToActive = document.querySelector(`[data-bs-theme-value="${theme}"]`)
        console.log(btnToActive)
        console.log(btnToActive.getAttribute('svg[data-icon]'));
        const svgOfActiveBtn = btnToActive.getAttribute('data-icon')

        document.querySelectorAll('[data-bs-theme-value]').forEach(element => {
            element.classList.remove('active')
            element.setAttribute('aria-pressed', 'false')
        })

        btnToActive.classList.add('active')
        btnToActive.setAttribute('aria-pressed', 'true')
        activeThemeIcon.setAttribute('href', svgOfActiveBtn)
        const themeSwitcherLabel = `${themeSwitcherText.textContent} (${btnToActive.dataset.bsThemeValue})`
        themeSwitcher.setAttribute('aria-label', themeSwitcherLabel)

        if (focus) {
            themeSwitcher.focus()
    }
    }

    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', () => {
        const storedTheme = getStoredTheme()
        if (storedTheme !== 'light' && storedTheme !== 'dark') {
            setTheme(getPreferredTheme())
        }
    })

    window.addEventListener('DOMContentLoaded', () => {
        showActiveTheme(getPreferredTheme())

        document.querySelectorAll('[data-bs-theme-value]')
                .forEach(toggle => {
                    toggle.addEventListener('click', () => {
                        console.log('Theme control clicked ! ');
                        const theme = toggle.getAttribute('data-bs-theme-value')
                        setStoredTheme(theme)
                        setTheme(theme)
                        showActiveTheme(theme, true)
                    })
                })
    })



    $('#msbo').on('click', function () {
        $('body').toggleClass('msb-x');
    });



})()

jQuery(function ($) {



    console.log('start jquery !');


    /**
     * Sidebar 
     * Check if sidebar left is collapsed by checking class 'sidebarCollapseOn'
     * 
     * @returns {Boolean} true if collapse (hide) and false ifnot 
     */
    const isSidebarCollapsed = () => {
        return $('.sidebar-wrapper').hasClass('toggled') ? true : false;
    }



    //a longer winded version of the above
    function verticalScrollPresent()
    {
        return (document.documentElement.scrollHeight !== document.documentElement.clientHeight);

        // Get the computed style of the body element
        /*var cStyle = document.body.currentStyle || window.getComputedStyle(document.body, "");
         
         // Check the overflow and overflowY properties for "auto" and "visible" values
         var hasVScroll = cStyle.overflow == "visible"
         || cStyle.overflowY == "visible"
         || (hasVScroll && cStyle.overflow == "auto")
         || (hasVScroll && cStyle.overflowY == "auto");
         
         return !hasVScroll;*/
    }


    function getScrollbarWidth() {
        return (window.innerWidth - document.documentElement.clientWidth);
    }

    /**
     * Breakpoint          Class infix Dimensions
     * X-Small             None	<576px
     * Small               sm          ≥576px
     * Medium              md          ≥768px
     * Large               lg          ≥992px
     * Extra large         xl          ≥1200px
     * Extra extra large   xxl         ≥1400px
     * 
     * (xs: 0, sm: 576px, md: 768px, lg: 992px, xl: 1200px)
     * 
     * @returns {undefined}
     */
    function getBootstrapBreakpoint() {
        let windWidth = $(window).width();
        let wsize = windWidth;

//        let windHeight = $(window).height();
//        let wrapperWidth = $('#wrapper').width();
//        let wrapperHeight = $('#wrapper').height();

//        console.log('windows', '(' + windWidth + ', ' + windHeight + ')');
//        console.log('wrapper', '(' + wrapperWidth + ', ' + wrapperHeight + ')');
//
//        if (wrapperHeight > windHeight) {
//            wsize = windWidth + 17;
//        }

        if (verticalScrollPresent()) {
            let windScrollWidth = getScrollbarWidth();
            wsize = windWidth + windScrollWidth;
            //         console.log('verticalScrollPresent', 'Yes vertical scroll = ' + windScrollWidth);
        } else {
            //         console.log('verticalScrollPresent', 'No vertical scroll !');
        }


        if (wsize >= 1400) {
            return 'xxl';
        } else if (wsize >= 1200) {
            return 'xl';
        } else if (wsize >= 992) {
            return 'lg';
        } else if (wsize >= 768) {
            return 'md';
        } else if (wsize >= 576) {
            return 'sm';
        } else {
            return 'None';
        }
    }


    /**
     * Manage action depend on Bootstrap Breakpoint
     * 
     * @returns {undefined}
     */
    function manageBootstrapBreakpoint() {
        var screen = getBootstrapBreakpoint();
        //console.log('colorlib.js >> manageBootstrapBreakpoint','screen = ' + screen);
        switch (screen) {
            case ('None'):
                //console.log('doManageBootstrapBreakpoint', 'None');
                break;
            case ('sm'):
                var isSidebarCollapsedVar = isSidebarCollapsed();
                console.log('doManageBootstrapBreakpoint', 'sm  >> with remove = ' + isSidebarCollapsedVar);
                if (!isSidebarCollapsed()) {
                    $(".page-wrapper").removeClass("toggled");
                    console.log("REMOVE TOGGLE !");
                }
                break;
            case ('md'):
                var isSidebarCollapsedVar = isSidebarCollapsed();
                console.log('doManageBootstrapBreakpoint', 'md with add = ' + isSidebarCollapsedVar);
                if (!isSidebarCollapsed()) {
                   $(".page-wrapper").addClass("toggled");
                   console.log("ADD TOGGLE !");
                }
                break;
            case ('lg'):
                //console.log('doManageBootstrapBreakpoint', 'lg');
                break;
            case ('xl'):
                //console.log('doManageBootstrapBreakpoint', 'xl');
                break;
            case ('xxl'):
                //console.log('doManageBootstrapBreakpoint', 'xxl');
                break;
            default:
                console.log('colorlib.js >> manageBootstrapBreakpoint', 'Bootstrap breakpoint error')
                break;
        }



    }
    manageBootstrapBreakpoint();


    $(window).on('resize', manageBootstrapBreakpoint);










    $(".sidebar-dropdown > a").click(function () {
        $(".sidebar-submenu").slideUp(200);
        if (
                $(this)
                .parent()
                .hasClass("active")
                ) {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                    .parent()
                    .removeClass("active");
        } else {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                    .next(".sidebar-submenu")
                    .slideDown(200);
            $(this)
                    .parent()
                    .addClass("active");
        }
    });

    $("#close-sidebar").click(function () {
        $(".page-wrapper").removeClass("toggled");
    });
    $("#show-sidebar").click(function () {
        $(".page-wrapper").addClass("toggled");
    });

    $(".custom-menu").click(function () {
        $(".page-wrapper").toggleClass("toggled");
    });



    console.log('end startup jquery !');
});




