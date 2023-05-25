// 获取设备宽度
const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;

// 获取CSS样式表
const styleSheet = document.styleSheets[0];

// 遍历CSS规则
for (let i = 0; i < styleSheet.rules.length; i++) {
    const rule = styleSheet.rules[i];

    // 如果是针对移动设备的规则，则修改规则中的vh/vw为百分比
    if (rule.media && rule.media.mediaText.indexOf('max-width') !== -1) {
        for (let j = 0; j < rule.cssRules.length; j++) {
            const cssRule = rule.cssRules[j];
            if (cssRule.style) {
                cssRule.style.height = cssRule.style.height.replace('vh', '%');
                cssRule.style.width = cssRule.style.width.replace('vw', '%');
            }
        }
    }
}
