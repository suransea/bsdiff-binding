Pod::Spec.new do |s|
  s.name         = "BSDiff"
  s.version      = "0.0.1"
  s.summary      = "bsdiff library"

  s.homepage     = "https://github.com/suransea/bsdiff-binding.git"
  s.license      = "MIT"
  s.author       = { "sea" => "simpleslight@icloud.com" }
  s.source       = { :git => "https://github.com/suransea/bsdiff-binding.git", :tag => "#{s.version}" }

  s.ios.deployment_target = '11.0'
  s.osx.deployment_target = '10.13'

  s.subspec 'BSDiffC' do |sp|
    sp.source_files = "bsdiff/bsdiff.c", "bsdiff/bspatch.c", "bsdiff/include/**/*.h"
    sp.public_header_files = "bsdiff/include/**/*.h"
    sp.header_mappings_dir = 'bsdiff/include'
    sp.library = "bz2"
  end

  s.subspec 'BSDiffObjC' do |sp|
    sp.source_files = "bsdiff-objc/**/*.{h,m}", "bsdiff-objc/include/**/*.h"
    sp.public_header_files = "bsdiff-objc/include/**/*.h"
    sp.header_mappings_dir = 'bsdiff-objc/include'
    sp.dependency "BSDiff/BSDiffC"
  end

  s.subspec 'BSDiffSwift' do |sp|
    sp.source_files = "bsdiff-swift/Sources/BSDiff/**/*.swift"
    sp.dependency "BSDiff/BSDiffC"
  end

  s.test_spec 'BSDiffSwiftTests' do |sp|
    sp.source_files = "bsdiff-swift/Tests/BSDiffTests/**/*.swift"
    sp.dependency "BSDiff/BSDiffSwift"
  end

  s.app_spec 'bsdiff' do |sp|
    sp.source_files = "bsdiff/bsdiff_main.c"
    sp.dependency "BSDiff/BSDiffC"
  end

  s.app_spec 'bspatch' do |sp|
    sp.source_files = "bsdiff/bspatch_main.c"
    sp.dependency "BSDiff/BSDiffC"
  end
end
