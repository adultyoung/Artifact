// vue.config.js
module.exports = {
        runtimeCompiler: true,
        devServer: {
                proxy: {
                        '^/auth/oauth': {
                                target: 'localhost:8091/auth/oauth',
                                ws: true,
                                changeOrigin: true
                        },
                },
        },
        configureWebpack: {
                devServer: {
                        allowedHosts: ['http://localhost:8091']
                }
        }
}