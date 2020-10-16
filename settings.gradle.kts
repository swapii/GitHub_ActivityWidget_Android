val rootProjectPathLength = rootDir.absolutePath.length

rootDir.findAllPotentialModuleDirs()
    .filter { it.listFiles().any { child -> child.name.startsWith("build.gradle") } }
    .filter { it.name != "buildSrc" }
    .forEach { moduleDir ->

        val moduleName =
            moduleDir.absolutePath.substring(rootProjectPathLength)
                .replace(File.separator, "-")
                .replaceFirst('-', ':')

        include(moduleName)

        project(moduleName).projectDir = moduleDir

    }

fun File.findAllPotentialModuleDirs(): Sequence<File> =
    listFiles()
        .asSequence()
        .filter { it.isDirectory }
        .filter { !it.isHidden }
        .filter { !it.name.startsWith('.') }
        .filter { it.name != "src" && it.name != "build" }
        .flatMap { sequenceOf(it) + it.findAllPotentialModuleDirs() }
