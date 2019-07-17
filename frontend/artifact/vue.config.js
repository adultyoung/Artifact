// vue.config.js

module.exports = {

        runtimeCompiler: true,
        devServer: {
                hotOnly: true,
                clientLogLevel: 'warning',
                public: 'http://localhost:8080',
        },

}