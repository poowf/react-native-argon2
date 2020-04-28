require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNArgon2"
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']

  s.authors      = package['author']
  s.homepage     = package['homepage']

  s.source       = { :git => "https://github.com/poowf/react-native-argon2.git", :tag => "v#{s.version}" }
  s.source_files  = "ios/**/*.{h,m}"
  s.platform          = :ios, '10.0'

  s.dependency 'React'
  s.dependency 'Argon2'
  s.static_framework = true
end
